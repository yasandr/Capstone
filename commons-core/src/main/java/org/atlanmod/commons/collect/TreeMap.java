package org.atlanmod.commons.collect;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static java.util.Collections.emptyList;
import static org.atlanmod.commons.Guards.checkNotNull;

@ParametersAreNonnullByDefault
public class TreeMap<K, V> {
    private final Node root = new RootNode();

    public V put(Path<K> keys, V value) {
        return root.put(checkNotNull(keys), checkNotNull(value));
    }

    public Optional<V> get(Path<K> keys) {
        return root.get(checkNotNull(keys));
    }

    @Override
    public String toString() {
        return root.toString();
    }

    abstract class Node {
        private List<ContentNode> nodes = emptyList();

        public V put(Path<K> keys, V value) {
            ContentNode child;
            K key = keys.head();
            int position = this.indexOf(key);

            if (position < 0) {
                child = addChild(key);
            } else {
                child = nodes.get(position);
            }

            if (keys.size() == 1) {
                child.setValue(value);
            } else {
                child.put(keys.tail(), value);
            }

            return value;
        }

        public Optional<V> get(Path<K> keys) {
            ContentNode child;
            K key = keys.head();
            int position = indexOf(key);

            if (position < 0) {
                return Optional.empty();
            } else {
                child = nodes.get(position);
            }

            return keys.size() == 1 ? child.getValue() : child.get(keys.tail());
        }

        private int indexOf(K key) {
            for (int i = 0; i < nodes.size(); i++) {
                ContentNode each = nodes.get(i);
                if (each.key.equals(key)) {
                    return i;
                }
            }
            return -1;
        }

        private ContentNode addChild(K key) {
            assert key != null;

            ContentNode newNode = new ContentNode(this, key);
            this.nodes().add(newNode);

            return newNode;
        }

        private List<ContentNode> nodes() {
            if (nodes.isEmpty()) {
                nodes = new ArrayList<>(3);
            }

            return nodes;
        }

        @Override
        public String toString() {
            if (nodes.isEmpty()) return "";

            StringBuilder builder = new StringBuilder();
            builder.append(": [").append(nodes.get(0));
            for (int i = 1; i < nodes.size(); i++) {
                builder.append(',').append(nodes.get(i));
            }
            builder.append(']');

            return builder.toString();
        }
    }

    class RootNode extends Node {
        @Override
        public String toString() {
            return "root" + super.toString();
        }
    }

    class ContentNode extends Node {
        private final Node parent;
        private final K key;
        private V value;

        ContentNode(Node parent, K key) {
            assert key != null;

            this.parent = parent;
            this.key = key;
        }

        void setValue(V value) {
            this.value = value;
        }

        Optional<V> getValue() {
            return Optional.ofNullable(value);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(key)
                    .append(": ")
                    .append(value)
                    .append(super.toString());

            return builder.toString();
        }
    }
}
