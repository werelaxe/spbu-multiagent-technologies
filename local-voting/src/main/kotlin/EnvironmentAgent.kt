package agents

import jade.core.Agent

class EnvironmentAgent: Agent() {
    override fun setup() {
        addBehaviour(ReceiveEnvironment(this))
    }
}
