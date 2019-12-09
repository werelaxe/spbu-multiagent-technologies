package agents

import jade.core.Profile
import jade.core.ProfileImpl
import jade.core.Runtime


const val AGENTS_COUNT = 10
const val LEADER_ID = "1"
const val ITERATIONS_COUNT = 100
const val ENVIRONMENT_ID = "environment"
val values = mutableListOf<Float>()


fun main() {
    val runtime = Runtime.instance()
    val profile = ProfileImpl()
    profile.setParameter(Profile.MAIN_HOST, "localhost")
    profile.setParameter(Profile.MAIN_PORT, "10098")
    val mainContainer = runtime.createMainContainer(profile)
    val agents = (1..AGENTS_COUNT).map { i ->
        mainContainer.createNewAgent(i.toString(), "agents.DefaultAgent", null)
    }
    mainContainer.createNewAgent(ENVIRONMENT_ID, "agents.EnvironmentAgent", null).start()
    agents.forEach { agent ->
        agent.start()
    }
}
