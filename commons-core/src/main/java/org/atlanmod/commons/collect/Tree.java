/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.collect;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static org.atlanmod.commons.Guards.checkNotNull;
import static org.atlanmod.commons.collect.MoreArrays.newArray;

/**
 * A Tree data structure, with a root value and subtrees of children with a parent node,
 * represented as a set of linked nodes.
 *
 * This data structure can represent hierarchical data such as:
 * - File paths: "/usr/local/bin"
 * - Domain names: "org.atlanmod.commons"
 * - Namespaces: "java.util.function.BiFunction"
 *
 * This Tree can be seen as a recursive collection of {@code Node}, where each node is itself a data structure
 * consisting of a content (or value), along with a ser of nodes, the children.
 * Nodes that do not have children are named <i>Leaves</i>.
 *
 * For instance:
 * <pre>
 * {@code
 *               r
 *             / | \
 *            a  b  c
 *           /|\    |
 *          d e f   g
 * }
 * </pre>
 *
 * In the example above, <tt>r</tt> is the root and <tt>b,d,e,f,g</tt> are leaves.
 * When traversing this tree, the {@code Iterator} will visit nodes <tt>a,b,c,d,e,f,g</tt> in this exact sequence.
 *
 *
 * The {@code Tree} provides an breadth-first {@code Iterator}.
 *
 * @param <T> the type of elements (node contents) in this tree.
 *
 * @author sunye
 * @since 1.1.0
 */
@ParametersAreNonnullByDefault
public class Tree<T> implements Iterable<T> {

    private final Node root = new RootNode();

    @SafeVarargs
    public final void add(T... path) {
        checkNotNull(path, "path");

        root.add(path);
    }

    /**
     * Returns {@code true} if this tree contains no elements, {@code false} otherwise
     *
     * @return {@code true} if this tree contains no elements
     */
    public boolean isEmpty() {
        return root.size == 0;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Returns a {@code List} containing the values (contents) of all leaf nodes of this tree
     *
     * @return a list of values
     */
    public List<T> leaves() {
        return root.leaves();
    }

    /**
     * Returns a breadth-first iterator over the elements in this tree
     *
     * @return an iterator
     */
    public Iterator<T> iterator() {
        return new BreadthFirstTreeIterator<>(this.root);
    }

    // region Node
    class RootNode extends Node {

        @Override
        public String toString() {
            return "ROOT" + super.toString();
        }

        void collectLeaves(List<T> collectedLeaves) {
            if (this.isLeaf()) return;

            for (int i = 0; i < size; i++) {
                nodes[i].collectLeaves(collectedLeaves);
            }
        }

        @Override
        public List<Node> path() {
            return new LinkedList<>();
        }
    }

     class ContentNode extends Node {
        private final Node parent;
        private final T content;

        public ContentNode(Node parent, T content) {
            this.parent = parent;
            this.content = content;
        }

        @Override
        public String toString() {
            return content + super.toString();
        }

        void collectLeaves(List<T> collectedLeaves) {

            if (this.isLeaf()) {
                collectedLeaves.add(this.content);
            } else {
                for (int i = 0; i < size; i++) {
                    nodes[i].collectLeaves(collectedLeaves);
                }
            }
        }

        @Override
        public List<Node> path() {

            List<Node>  ancestors = parent.path();
            ancestors.add(this);
            return ancestors;
        }
    }

    abstract class Node {
        ContentNode[] nodes = newArray(ContentNode.class, 0);
        int size = 0;

        private int indexOf(T value) {

            for (int i = 0; i < size; i++) {
                if (value.equals(nodes[i].content)) {
                    return i;
                }
            }
            return -1;
        }

        @SafeVarargs
        public final void add(T... path) {
            checkNotNull(path, "path");

            if (path.length == 0) return;
            Node child;

            int position = indexOf(MoreArrays.head(path));
            if (position < 0) {
                child = addChild(MoreArrays.head(path));
            } else {
                child = getChild(position);
            }

            child.add(MoreArrays.tail(path));
        }

        private Node addChild(T childContent) {
            ensureCapacity(size + 1);

            ContentNode newNode = new ContentNode(this, childContent);
            nodes[size++] = newNode;

            return newNode;
        }

        private Node getChild(int position) {
            assert position < nodes.length;

            return nodes[position];
        }

        private void ensureCapacity(int capacity) {
            if (capacity > nodes.length) {
                grow();
            }
        }

        private void grow() {
            int newCapacity = nodes.length + 10;
            nodes = Arrays.copyOf(nodes, newCapacity);
        }

        @Override
        public String toString() {
            if (size == 0) return "";

            StringBuilder builder = new StringBuilder();
            builder.append(": [").append(nodes[0]);
            for (int i = 1; i < size; i++) {
                builder.append(',').append(nodes[i]);
            }
            builder.append(']');

            return builder.toString();
        }

        public boolean isLeaf() {
            return size == 0;
        }


        abstract void collectLeaves(List<T> collectedLeaves);

        public List<T> leaves() {
            List<T> leaves = new LinkedList<>();
            this.collectLeaves(leaves);
            return leaves;
        }

        public abstract List<Node> path();
    }

    // endregion

    // region Iterator
    static class  BreadthFirstTreeIterator<T> implements Iterator<T> {

        private final ArrayDeque<Tree<T>.Node> stack = new ArrayDeque<>();
        private Tree<T>.Node current;
        private int cursor = 0;

        public BreadthFirstTreeIterator(Tree<T>.Node tree) {
            current = tree;
        }

        @Override
        public boolean hasNext() {
            return cursor != current.size || !stack.isEmpty();
        }

        @Override
        public T next() {
            if (cursor >= current.size && stack.isEmpty())
                throw new NoSuchElementException();

            if (cursor == current.size) {
                current = stack.removeLast();
                cursor = 0;
            }

            Tree<T>.ContentNode node = (Tree<T>.ContentNode) current.getChild(cursor);
            if (!node.isLeaf()) {
                stack.addFirst(node);
            }
            cursor++;
            return node.content;
        }
    }

    // endregion
}