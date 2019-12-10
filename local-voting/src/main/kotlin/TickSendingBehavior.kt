package agents

import jade.core.behaviours.TickerBehaviour

class TickSendingBehavior(private val agent: DefaultAgent, period: Long) : TickerBehaviour(agent, period) {
    override fun onTick() {
        for (linkedAgent in agent.linkedAgents) {
            agent.sendMessageViaEnvironment(agent.result.toString(), linkedAgent)
        }
    }
}
