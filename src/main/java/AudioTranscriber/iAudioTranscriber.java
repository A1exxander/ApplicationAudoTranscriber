package AudioTranscriber;

import java.io.IOException;

public interface iAudioTranscriber {
    public String transcribe(byte[] audioBytes) throws IOException;
}
