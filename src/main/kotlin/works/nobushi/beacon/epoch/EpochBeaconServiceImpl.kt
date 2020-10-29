package works.nobushi.beacon.epoch

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Clock
import java.time.Duration

@Service
class EpochBeaconServiceImpl(
    private val clock: Clock
) : EpochBeaconService {

    override fun epoch(): Flux<Epoch> = Flux.interval(Duration.ofSeconds(1))
        .map { clock.instant() }
        .map { it.toEpochMilli() }
        .map { Epoch(it) }
}
