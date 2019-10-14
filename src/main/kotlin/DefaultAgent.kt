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
    var received = 0
    private val value = Random.nextInt(0, 10)
    var result = value
    val leader
        get() = localName == LEADER_ID

    override fun setup() {
        if (leader) {
            received--
        }
        val id = aid.localName
        linkedAgents.addAll(linkedAgentsById[id] ?: throw Exception("!"))
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
                prn("I'm a leader! Result: $result")
                exitProcess(0)
            } else {
                sendMessage("Response:$result", parent!!)
            }
        } else {
            val newReceiver = linkedAgents.first()
            linkedAgents.remove(newReceiver)
            sendMessage("Request:0", newReceiver)
        }
    }
}
