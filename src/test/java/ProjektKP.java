import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProjektKP {


    static WebDriver driver;

    //driver.findElement(By.id("SubmitLogin")).click();
    @FindBy(id="SubmitLogin")
    private WebElement submitButton;

    @FindBy(id="passwd")
    private WebElement passwordInput;

    // driver.findElement(By.xpath("//*[@id='center_column']/div[1]")).getText())
    @FindBy(xpath = "//*[@id='center_column']/div[1]")
    private WebElement errorMessageNoUserName;

    // to jest potrzebne, aby móc używać @FindBy // tu musi być nazwa mojego projektu
    public ProjektKP() {
        PageFactory.initElements(driver,this);
    }

    @BeforeAll
    static void prepareBrowser(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @BeforeEach
    void goToAutomationPracticeSite(){
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterEach
    void deleteAllCookies(){
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    @Test
    // Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano loginu.
    void shouldVerifyLogInPageWithoutLoginString() throws InterruptedException {
        // znajduję i klikam przycisk Sign In
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();

        // sprawdzam, czy znajdujemy się na stronie logowania
        WebElement autenthicationPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", autenthicationPageTitle.getText());

        // wpisuję hasło
        WebElement passwordInput = driver.findElement(By.id("passwd"));
        passwordInput.sendKeys("LaCucaracha");

        // znajduję i klikam przycisk Sign In
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();

        // sprawdzam, czy wyświeta się oczekiwany alert
        Assertions.assertEquals("There is 1 error\nAn email address required.",
                driver.findElement(By.cssSelector(".alert-danger ")).getText());
        // WZIĘŁAM TEN CSS Z MATERIAŁÓW, ALE WYDAJE MI SIĘ, ŻE NA STRONIE TA KLASA WYSTĘPUJE DWUKROTNIE
    }

    @Test
    // Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano hasła.
    void shouldVerifyLogInPageWithoutPasswordString() throws InterruptedException {
        // znajduję i klikam przycisk Sign In
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();

        // sprawdzam, czy znajduję się na stronie logowania
        WebElement autenthicationPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", autenthicationPageTitle.getText());

        // wpisuję e-mail
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("LaCucaracha@test.pl");

        // znajduję i klikam przycisk Sign In
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();

        // sprawdzam, czy wyświeta się oczekiwany alert
        Assertions.assertEquals("There is 1 error\nPassword is required.",
                driver.findElement(By.cssSelector(".alert-danger ")).getText());
    }

    @Test
    //Sprawdź, czy strona główna oraz strona logowania zawiera logo i pole wyszukiwania.
    void shouldVerifyLogoAndSearchInputIsDisplayedOnHomePage () throws InterruptedException {
        // sprawdzam, czy logo znajduje się na stronie głównej
        WebElement logo = driver.findElement(By.cssSelector("#header_logo img"));
        Assertions.assertTrue(logo.isDisplayed());

        // sprawdzam, czy pole wyszukiwania znajduje się na stronie głównej
        WebElement searchInput = driver.findElement(By.id("search_query_top"));
        Assertions.assertTrue(searchInput.isEnabled());
    }

    @Test
    void shouldVerifyLogoAndSearchInputIsDisplayedOnLoginPage () throws InterruptedException {
        // przechodzę na stronę logowania
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();

        // sprawdzam, czy znajdujemy się na stronie logowania
        WebElement autenthicationPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", autenthicationPageTitle.getText());

        // sprawdzam, czy logo znajduje się na stronie logowania
        WebElement logo = driver.findElement(By.cssSelector("#header_logo img"));
        Assertions.assertTrue(logo.isDisplayed());

        // sprawdzam, czy pole wyszukiwania znajduje się na stronie logowania
        WebElement search = driver.findElement(By.id("search_query_top"));
        Assertions.assertTrue(search.isEnabled());
    }

    @Test
    // Napisz test sprawdzający przejście ze strony głównej do strony Kontakt
    void shouldVerifyRedirectFromHomeToContactSite() throws InterruptedException {
        // znajduję przycisk Contact Us i w niego klikam
        WebElement contactUsButton = driver.findElement(By.cssSelector("#contact-link"));
        Thread.sleep(2000);
        contactUsButton.click();

        // sprawdzam, czy znalazłam się na stronie kontakt
        WebElement contactUsBreadcrumb =
                driver.findElement(By.cssSelector("#columns span.navigation_page"));
        Thread.sleep(2000);
        Assertions.assertEquals("Contact", contactUsBreadcrumb.getText());
    }

    @Test
    // Napisz test sprawdzający przejście ze strony logowania do strony głównej
    void shouldVerifyRedirectFromLoginToHomePage() throws InterruptedException {
        // znajduję i klikam przycisk Sign In
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();

        // sprawdzam, czy znajdujemy się na stronie logowania
        WebElement autenthicationPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", autenthicationPageTitle.getText());

        // znajduję i klikam logo
        WebElement logo = driver.findElement(By.cssSelector("#header_logo img"));
        Thread.sleep(2000);
        logo.click();

        // sprawdzam, czy jestem na stronie głównej
        WebElement HomePageSlider =
                driver.findElement(By.id("homepage-slider"));
        Assertions.assertTrue(HomePageSlider.isDisplayed());
        Thread.sleep(2000);
    }

    // Piszę metodę dodającą produkt do koszyka - używam jej w 6. i 7. zadaniu
    // to najprostszy algorytm, który mi zadział przy dodawaniu sukienki do koszyka XD
    public static void addProductToTheCart() throws InterruptedException {
        // znajduję link do podstrony women i go klikam
        WebElement womenButton =
                driver.findElement(By.xpath("//a[contains(@title,'Women')]"));
        Thread.sleep(2000);
        womenButton.click();

        // sprawdzam, czy jestem na stronie women
        WebElement womenBreadcrumb = driver.findElement(By.className("navigation_page"));
        Assertions.assertEquals("Women", womenBreadcrumb.getText());

        // ustawiam odpowiedni widok produktów na stronie
        WebElement viewListButton = driver.findElement(By.className("icon-th-list"));
        viewListButton.click();

        // dodaję sukienkę do koszyka
        WebElement printedDress = driver.findElement(By.xpath
                ("//a[contains(@data-id-product,'4') and contains(@title,'Add to cart')]"));
        // driver.findElement(By.xpath ("//*[@id=\"center_column\"]/ul/li[4]/div/div/div[3]/div/div[2]/a[1]/span"));

        Thread.sleep(2000);
        printedDress.click();

        // przechodzę do koszyka
        WebElement cart =
                driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a"));
        Thread.sleep(2000);
        cart.click();
    }

    @Test
    // Napisz test, który dodaje produkt do koszyka. Zweryfikuj, czy dodanie powiodło się
    void shouldVerifyIfProductIsAddedToTheCart() throws InterruptedException {
        // dodaję produkt do koszyka
        addProductToTheCart();

        // sprawdzam, czy produkt został dodany
        WebElement totalPrice = driver.findElement(By.id("total_price"));
        Thread.sleep(2000);
        Assertions.assertEquals("$52.99", totalPrice.getText());
    }

    @Test
    // Napisz test, który dodaje produkt do koszyka, a następnie usuwa go.
    // Zweryfikuj, czy usunięcie powiodło się
    void shouldVerifyIfProductIsDeletedFromTheCart() throws InterruptedException {
        // dodaję produkt do koszyka
        addProductToTheCart();

        // usuwam produkt z koszyka
        WebElement trashButton= driver.findElement(By.className("cart_quantity_delete"));
        Thread.sleep(2000);
        trashButton.click();
        wait.until(ExpectedConditions.invisibilityOf(trashButton));

        // sprawdzam, czy produkt został usunięty
        Assertions.assertEquals("Your shopping cart is empty.",
                driver.findElement(By.cssSelector(".alert-warning")).getText());

    }

    //Zrefaktoruj logowanie. Utwórz metodę pomocniczą login(), która przyjmuje dwa parametry:
    // login i hasło. Użyj jej w teście sprawdzającym logowanie.
    public static void login (String login, String password) throws InterruptedException {
        // znajduję i klikam przycisk Sign In
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();

        // sprawdzam, czy znajdujemy się na stronie logowania
        WebElement autenthicationPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", autenthicationPageTitle.getText());

        // wpisuję e-mail
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(login);

        // wpisuję hasło
        WebElement passwordInput = driver.findElement(By.id("passwd"));
        passwordInput.sendKeys(password);

        // klikam przycisk Sign In
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();
    }

    @Test
    void shouldVerifyLogIn() throws InterruptedException {
        login("test@softie.pl", "1qaz!QAZ");
        //asercja do poprawnego logowania:
        WebElement myAccountPageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("MY ACCOUNT", myAccountPageTitle.getText());
    }

}
