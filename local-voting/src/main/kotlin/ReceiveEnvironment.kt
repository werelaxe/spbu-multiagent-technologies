package agents

import jade.core.behaviours.CyclicBehaviour
import jade.lang.acl.ACLMessage
import kotlin.random.Random

class ReceiveEnvironment(private val agent: EnvironmentAgent): CyclicBehaviour(agent) {
    override fun action() {
        val message: ACLMessage? = agent.receive()
        message?.let {
            val (receiverId, messageContent) = message.content.split(":")
            val noisedValue = ((messageContent.toFloat() + (Random.nextFloat() - 0.5) * NOISE_SCALE)).toString()
            if (Random.nextFloat() > LOOSING_PACKAGE_PROBABILITY) {
                agent.sendMessage(noisedValue, receiverId)
            }
            block()
        }
    }
}
