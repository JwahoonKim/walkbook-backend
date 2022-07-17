package walkbook.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import walkbook.repository.UserRepository;

public class Learn {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void user() {
    }

}
