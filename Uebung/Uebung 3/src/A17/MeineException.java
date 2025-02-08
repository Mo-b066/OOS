package A17;
// a)
import java.io.IOException;

public class MeineException extends IOException {
    MeineException(String meldung){super(meldung);
    }
  public void ExWerfenUndWeiterleiten() throws MeineException{

    throw new MeineException("Das ist eine Exception");
  }

}
