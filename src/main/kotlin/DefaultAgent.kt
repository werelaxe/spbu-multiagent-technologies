package agents

import jade.core.AID
import jade.core.Agent
import jade.lang.acl.ACLMessage
import java.lang.Exception
import kotlin.random.Random
import kotlin.system.exitProcess


class DefaultAgent: Agent() {
    val linkedAgents = mutableSetOf<String>()
    var parent: String? = null
    private val value = Random.nextInt(0, 100)
    var result = value
    private val leader
        get() = localName == LEADER_ID

    override fun setup() {
        val id = aid.localName
        linkedAgents.addAll(linkedAgentsById[id] ?: throw Exception("Wrong agent id"))
        println("Agent $localName has been registered, value: $value, linked: $linkedAgents")
        addBehaviour(SendMessage(this))
        addBehaviour(ReceiveMessage(this))
    }

    fun sendMessage(message: String, receiverId: String) {
        val msg = ACLMessage(ACLMessage.INFORM)
        val dest = AID(receiverId, false)
        msg.content = message
        msg.addReceiver(dest)
        send(msg)
    }

    fun prn(message: String) {
        println("$localName: $message")
    }

    fun nextStep() {
        if (linkedAgents.isEmpty()) {
            if (leader) {
                prn("I'm a leader! Result: ${result / AGENTS_COUNT}")
                exitProcess(0)
            } else {
                sendMessage("Response:$result", parent ?: throw Exception("Parent can not be null"))
            }
        } else {
            val newReceiver = linkedAgents.first()
            linkedAgents.remove(newReceiver)
            sendMessage("Request:0", newReceiver)
        }
    }

    companion object {
        val linkedAgentsById = mutableMapOf<String, MutableSet<String>>()

        init {
            for (i in 1..AGENTS_COUNT) {
                val next = ((i % AGENTS_COUNT) + 1)
                val prev = ((i - 2 + AGENTS_COUNT) % AGENTS_COUNT + 1)
                val linkedAgents = mutableSetOf(next, prev)
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
