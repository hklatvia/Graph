fun main() {
    val edges = listOf(
        Triple("Car", "Bus", 2),
        Triple("Megabus", "MegaCar", 3),
        Triple("Train", "plain", 4)
    )
    val edges1 = listOf(
        Triple("a", "b", 5),
        Triple("c", "d", 6),
        Triple("x", "y", 7)
    )
    val graph1 = GraphImpl(edges1)
    graph1.addEdge("c", "u", 9)
    graph1.addEdge("j", "u", 10)
    graph1.printGraph()
    println( graph1.toEdges())

}