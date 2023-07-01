import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GraphTests {
    private val edges = listOf(
        Triple("Car", "Bus", 2),
        Triple("Bus", "MegaBus", 3),
        Triple("MegaBus", "plain", 4)
    )
    private val edges1 = listOf(
        Triple("a", "b", 5),
        Triple("c", "d", 6),
        Triple("x", "y", 7)
    )

    @Test
    fun toEdge() {
        val result = GraphImpl(edges).toEdges()
        val expectedResult = listOf(
            Triple("Car", "Bus", 2),
            Triple("Bus", "MegaBus", 3),
            Triple("MegaBus", "plain", 4)
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun addEdge() {
        val graph = GraphImpl(edges1)
        val expectedResult = listOf(
            Triple("a", "b", 5),
            Triple("c", "d", 6),
            Triple("x", "y", 7),
            Triple("c", "u", 8),
        ).toSet()
        graph.addEdge("c", "u", 8)
        val result = graph.toEdges().toSet()
        assertEquals(expectedResult, result)
    }

    @Test
    fun changeEdge() {
        val graph = GraphImpl(edges1)
        val expectedResult = listOf(
            Triple("a", "b", 10),
            Triple("c", "d", 6),
            Triple("x", "y", 7),
        ).toSet()
        graph.updateEdge("a", "b", 10)
        val result = graph.toEdges().toSet()
        assertEquals(expectedResult, result)
    }


    @Test
    fun removeEdge() {
        val graph = GraphImpl(edges1)
        val expectedResult = listOf(
            Triple("a", "b", 5),
            Triple("c", "d", 6),
        ).toSet()
        graph.removeEdge("x", "y")
        val result = graph.toEdges().toSet()
        assertEquals(expectedResult, result)
    }

    @Test
    fun findShortestWay() {
        val graph = GraphImpl(edges)
        val expectedResult = listOf(0, 2, 5, 9)
        val result = graph.findShortestWay("Car")
        assertEquals(expectedResult, result)
    }

    @Test
    fun plus() {
        val expectedResult = listOf(
            Triple("Car", "Bus", 2),
            Triple("Bus", "MegaBus", 3),
            Triple("MegaBus", "plain", 4),
            Triple("a", "b", 5),
            Triple("c", "d", 6),
            Triple("x", "y", 7)
        ).toSet()
        val graph = GraphImpl(edges)
        val graph1 = GraphImpl(edges1)
        val result = (graph + graph1).toEdges().toSet()
        assertEquals(expectedResult, result)
    }

}