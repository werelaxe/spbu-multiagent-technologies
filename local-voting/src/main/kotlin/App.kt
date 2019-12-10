package agents

import jade.core.Profile
import jade.core.ProfileImpl
import jade.core.Runtime


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
