package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String instanceIndex;
    private String instanceAddress;

    public EnvController(@Value("${PORT:NOT SET}") String port, @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit, @Value("${CF_INSTANCE_INDEX:NOT SET}") String instanceIndex, @Value("${CF_INSTANCE_ADDR:NOT SET}") String instanceAddress) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.instanceIndex = instanceIndex;
        this.instanceAddress = instanceAddress;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map outMap = new HashMap<String, String>();
        outMap.put("PORT", this.port);
        outMap.put("MEMORY_LIMIT", this.memoryLimit);
        outMap.put("CF_INSTANCE_INDEX", this.instanceIndex);
        outMap.put("CF_INSTANCE_ADDR", this.instanceAddress);
        return outMap;
    }
}
