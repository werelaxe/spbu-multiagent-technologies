package agents

import jade.core.behaviours.CyclicBehaviour
import jade.lang.acl.ACLMessage
import kotlin.math.abs
import kotlin.system.exitProcess

class ReceiveMessage(private val agent: DefaultAgent): CyclicBehaviour(agent) {
    override fun action() {
        val message: ACLMessage? = agent.receive()
        message?.let {
            agent.count++
//            agent.prn("Received message: '${message.content}' from ${message.sender.localName}")
            val linkedValue = message.content.toFloat()

            if (agent.count >= ITERATIONS_COUNT) {
                if (agent.leader) {
                    println("Result: ${agent.result}!")
                    println("Diff: ${abs(values.sum() / AGENTS_COUNT - agent.result)}")
                    exitProcess(0)
                }
            }
            agent.result += (linkedValue - agent.result) / 2f
            for (linkedAgent in agent.linkedAgents) {
                agent.sendMessageViaEnvironment(agent.result.toString(), linkedAgent)
            }
            block()
        }
    }
}
