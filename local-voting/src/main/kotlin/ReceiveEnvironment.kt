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
                if (Random.nextFloat() < EXTRA_DELAY_PROBABILITY) {
                    Thread {
                        Thread.sleep(COMMON_DELAY * EXTRA_DELAY_SCALE)
                        agent.sendMessage(noisedValue, receiverId)
                    }
                        .start()
                } else {
                    agent.sendMessage(noisedValue, receiverId)
                }
            }
            block()
        }
    }
}
