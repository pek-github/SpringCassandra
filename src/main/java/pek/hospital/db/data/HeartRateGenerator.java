package pek.hospital.db.data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.datastax.driver.core.LocalDate;

import pek.hospital.domain.HeartRate;


@Component
public class HeartRateGenerator {

    private static final long SECONDS_IN_MINUTE = 60L;

    public List<HeartRate> generate(final Patient patient,
                                    final String bedId,
                                    final HeartRateExtremes heartRateExtremes,
                                    final Interval interval) {

        final Random random = new Random();
        final PrimitiveIterator.OfInt randomHeartRatesGenerator
                = random.ints(heartRateExtremes.getMin(), heartRateExtremes.getMax() + 1).iterator();

        long intervalSeconds = interval.getMinutesStep() * SECONDS_IN_MINUTE;
        List<HeartRate> samples = new LinkedList<>();

        for (Instant current = interval.getFrom(); !current.isAfter(interval.getTo()); current = current.plusSeconds(intervalSeconds)) {

            Instant days = current.truncatedTo(ChronoUnit.DAYS);
            LocalDate date = LocalDate.fromMillisSinceEpoch(days.toEpochMilli());
            long secondOfDay = current.truncatedTo(ChronoUnit.MINUTES).getEpochSecond() - days.getEpochSecond();
            long minuteOfDay = secondOfDay / SECONDS_IN_MINUTE;

            int randomHeartRate = randomHeartRatesGenerator.nextInt();

            HeartRate sample = new HeartRate(patient.getPatientId(),
                                             date,
                                             (int) minuteOfDay,
                                             patient.getPatientLastName(),
                                             patient.getPatientFirstName(),
                                             bedId,
                                             randomHeartRate);

            samples.add(sample);
        }

        return samples;
    }


}
