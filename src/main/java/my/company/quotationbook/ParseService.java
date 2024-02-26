package my.company.quotationbook;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParseService {
    @Value("${app.src-file-path}")
    private String srcFilePath;
    private final Random random;
    public final List<String> quotas = new ArrayList<>();

    @PostConstruct
    void init() throws IOException {
        File file = new ClassPathResource(srcFilePath).getFile();

        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        quotas.addAll(Arrays.stream(content.split(System.lineSeparator())).toList());
        log.info("");
    }


    public String getQuota() {
        return quotas.get(random.nextInt(quotas.size()-1));
    }
}

