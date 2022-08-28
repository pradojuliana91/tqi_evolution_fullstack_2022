package simulations

import io.gatling.core.Predef.{rampUsers, _}
import scenarios.{CreateAuthorScenario, CreateBookScenario, CreateClientScenario}


class CreateBookSimulation extends BaseSimulation {
  setUp(
    CreateAuthorScenario.create.inject(rampUsers(2) during 2)
      .protocols(httpConf),

    CreateBookScenario.create.inject(rampUsers(2) during 2)
      .protocols(httpConf),

    CreateClientScenario.create.inject(rampUsers(2) during 2)
      .protocols(httpConf)
  )
}
