package agents

import jade.core.Agent
import kotlin.random.Random


class DefaultAgent: Agent() {
    val linkedAgents = mutableSetOf<String>()
    var count = 0
    private val value = Random.nextInt(0, 100).toFloat()
    var result = value
    val leader
        get() = localName == LEADER_ID

    override fun setup() {
        val id = aid.localName
        linkedAgents.addAll(linkedAgentsById[id] ?: throw Exception("Wrong agent id"))
        println("Agent $localName has been registered, value: $value, linked: $linkedAgents")
        values.add(value)
        addBehaviour(SendMessage(this))
        addBehaviour(ReceiveMessage(this))
    }

    fun sendMessageViaEnvironment(message: String, receiverId: String) {
        sendMessage("$receiverId:$message", ENVIRONMENT_ID)
    }

    companion object {
        val linkedAgentsById = mutableMapOf<String, MutableSet<String>>()

        init {
            for (i in 1..AGENTS_COUNT) {
                val next = ((i % AGENTS_COUNT) + 1)
                val linkedAgents = mutableSetOf(next)
                linkedAgentsById[i.toString()] = linkedAgents.map { it.toString() }.toMutableSet()
                for (j in 1..3) {
                    linkedAgentsById[i.toString()]?.add(Random.nextInt(1, AGENTS_COUNT + 1).toString())
                        ?: throw Exception("Incorrect id: $i")
                }
            }
            for (i in 1..AGENTS_COUNT) {
                linkedAgentsById[i.toString()]?.remove(i.toString())
                    ?: throw Exception("Incorrect id: $i")
            }
        }
    }
}
