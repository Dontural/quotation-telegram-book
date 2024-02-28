package my.company.quotationbook;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
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
        InputStream fstream = this.getClass().getResourceAsStream(srcFilePath);

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (fstream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        quotas.addAll(Arrays.stream(textBuilder.toString().split(System.lineSeparator())).toList());
        log.info("");
    }


    public String getQuota() {
        return quotas.get(random.nextInt(quotas.size() - 1));
    }
}

