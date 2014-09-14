package ch.docksnet.verify.example;

/**
 * @author Stefan Zeller
 */
public class MainService {
    private OtherService otherService;

    public MainService(OtherService otherService) {
        this.otherService = otherService;
    }

    public long square(Long input) {
        return otherService.square(input);
    }
}
