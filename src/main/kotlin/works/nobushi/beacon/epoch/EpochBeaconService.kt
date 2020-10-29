package works.nobushi.beacon.epoch

import reactor.core.publisher.Flux

interface EpochBeaconService {
    fun epoch(): Flux<Epoch>
}
