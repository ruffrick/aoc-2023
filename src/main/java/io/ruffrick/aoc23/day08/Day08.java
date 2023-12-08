package io.ruffrick.aoc23.day08;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Title("--- Day 8: Haunted Wasteland ---")
public class Day08 extends Solution {
    private final Direction[] directions;
    private final List<Node> nodes;

    public Day08() {
        this.directions = input.get(0)
                .chars()
                .mapToObj(codePoint -> switch ((char) codePoint) {
                    case 'L' -> Direction.LEFT;
                    case 'R' -> Direction.RIGHT;
                    default -> throw new IllegalStateException("Unexpected value: " + (char) codePoint);
                })
                .toArray(Direction[]::new);
        final Pattern pattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");
        final Map<String, PartialNode> nodes = input.stream()
                .skip(2)
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .collect(Collectors.toMap(
                        matcher -> matcher.group(1),
                        matcher -> new PartialNode(new Node(matcher.group(1)), matcher.group(2), matcher.group(3))
                ));
        this.nodes = nodes.values()
                .stream()
                .map(partialNode -> {
                    final Node node = partialNode.node();
                    node.setLeft(nodes.get(partialNode.left()).node());
                    node.setRight(nodes.get(partialNode.right()).node());
                    return node;
                })
                .toList();
    }

    @Override
    @Question("How many steps are required to reach ZZZ?")
    public String partOne() {
        Node node = nodes.stream().filter(n -> n.getLabel().equals("AAA")).findAny().orElseThrow();
        for (int i = 0; ; i++) {
            node = switch (directions[i % directions.length]) {
                case LEFT -> node.getLeft();
                case RIGHT -> node.getRight();
            };
            if (node.getLabel().equals("ZZZ")) {
                return Integer.toString(i + 1);
            }
        }
    }

    @Override
    @Question("How many steps does it take before you're only on nodes that end with Z?")
    public String partTwo() {
        final Node[] nodes = this.nodes.stream()
                .filter(node -> node.getLabel().endsWith("A"))
                .toArray(Node[]::new);
        this.nodes.stream()
                .filter(node -> node.getLabel().endsWith("Z"))
                .forEach(node -> node.setEnd(true));
        for (int i = 0; ; i++) {
            final Direction direction = directions[i % directions.length];
            boolean end = true;
            for (int j = 0; j < nodes.length; j++) {
                final Node node = nodes[j];
                final Node next = switch (direction) {
                    case LEFT -> node.getLeft();
                    case RIGHT -> node.getRight();
                };
                nodes[j] = next;
                end &= next.isEnd();
            }
            if (end) {
                return Integer.toString(i + 1);
            }
        }
    }
}
