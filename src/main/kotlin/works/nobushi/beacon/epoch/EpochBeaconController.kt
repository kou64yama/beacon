package works.nobushi.beacon.epoch

import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class EpochBeaconController(
    private val service: EpochBeaconService
) {

    @GetMapping("/epoch", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    @MessageMapping("epoch")
    fun epoch(): Flux<Epoch> = service.epoch()
}
