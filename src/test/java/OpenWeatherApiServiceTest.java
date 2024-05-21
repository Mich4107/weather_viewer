import org.junit.jupiter.api.Test;
import ru.yuubi.weather_viewer.dto.LocationDTO;
import ru.yuubi.weather_viewer.exception.WeatherApiCallException;
import ru.yuubi.weather_viewer.service.OpenWeatherApiService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenWeatherApiServiceTest {

    private static final String response = """
                [
                    {
                        "name": "Moscow",
                        "local_names": {
                            "mk": "Москва",
                            "sv": "Moskva",
                            "ko": "모스크바",
                            "sq": "Moska",
                            "bs": "Moskva",
                            "fi": "Moskova",
                            "bi": "Moskow",
                            "st": "Moscow",
                            "bn": "মস্কো",
                            "be": "Масква",
                            "it": "Mosca",
                            "ba": "Мәскәү",
                            "ky": "Москва",
                            "nl": "Moskou",
                            "lv": "Maskava",
                            "ascii": "Moscow",
                            "es": "Moscú",
                            "dv": "މޮސްކޯ",
                            "gv": "Moscow",
                            "mi": "Mohikau",
                            "ku": "Moskow",
                            "te": "మాస్కో",
                            "sc": "Mosca",
                            "ca": "Moscou",
                            "bo": "མོ་སི་ཁོ།",
                            "kn": "ಮಾಸ್ಕೋ",
                            "ja": "モスクワ",
                            "mr": "मॉस्को",
                            "ce": "Москох",
                            "zu": "IMoskwa",
                            "eo": "Moskvo",
                            "os": "Мæскуы",
                            "nn": "Moskva",
                            "ht": "Moskou",
                            "no": "Moskva",
                            "hu": "Moszkva",
                            "ga": "Moscó",
                            "fy": "Moskou",
                            "jv": "Moskwa",
                            "co": "Moscù",
                            "ar": "موسكو",
                            "ay": "Mosku",
                            "kk": "Мәскеу",
                            "ab": "Москва",
                            "ml": "മോസ്കോ",
                            "ie": "Moskwa",
                            "feature_name": "Moscow",
                            "sm": "Moscow",
                            "sr": "Москва",
                            "nb": "Moskva",
                            "kl": "Moskva",
                            "mg": "Moskva",
                            "kw": "Moskva",
                            "cv": "Мускав",
                            "da": "Moskva",
                            "se": "Moskva",
                            "cs": "Moskva",
                            "vi": "Mát-xcơ-va",
                            "br": "Moskov",
                            "et": "Moskva",
                            "mn": "Москва",
                            "af": "Moskou",
                            "sh": "Moskva",
                            "gn": "Mosku",
                            "zh": "莫斯科",
                            "hy": "Մոսկվա",
                            "ln": "Moskú",
                            "tl": "Moscow",
                            "cy": "Moscfa",
                            "qu": "Moskwa",
                            "id": "Moskwa",
                            "yi": "מאסקווע",
                            "ps": "مسکو",
                            "vo": "Moskva",
                            "wa": "Moscou",
                            "sg": "Moscow",
                            "my": "မော်စကိုမြို့",
                            "sl": "Moskva",
                            "eu": "Mosku",
                            "ak": "Moscow",
                            "mt": "Moska",
                            "sk": "Moskva",
                            "ru": "Москва",
                            "en": "Moscow",
                            "uk": "Москва",
                            "yo": "Mọsko",
                            "ro": "Moscova",
                            "an": "Moscú",
                            "ur": "ماسکو",
                            "ug": "Moskwa",
                            "tg": "Маскав",
                            "is": "Moskva",
                            "lg": "Moosko",
                            "pl": "Moskwa",
                            "kv": "Мӧскуа",
                            "am": "ሞስኮ",
                            "el": "Μόσχα",
                            "so": "Moskow",
                            "ta": "மாஸ்கோ",
                            "gd": "Moscobha",
                            "fo": "Moskva",
                            "hr": "Moskva",
                            "bg": "Москва",
                            "na": "Moscow",
                            "lt": "Maskva",
                            "ty": "Moscou",
                            "tk": "Moskwa",
                            "la": "Moscua",
                            "ka": "მოსკოვი",
                            "fr": "Moscou",
                            "li": "Moskou",
                            "az": "Moskva",
                            "su": "Moskwa",
                            "kg": "Moskva",
                            "ch": "Moscow",
                            "tr": "Moskova",
                            "pt": "Moscou",
                            "gl": "Moscova - Москва",
                            "fa": "مسکو",
                            "za": "Moscow",
                            "dz": "མོསི་ཀོ",
                            "av": "Москва",
                            "wo": "Mosku",
                            "th": "มอสโก",
                            "de": "Moskau",
                            "io": "Moskva",
                            "he": "מוסקווה",
                            "sw": "Moscow",
                            "cu": "Москъва",
                            "ss": "Moscow",
                            "uz": "Moskva",
                            "hi": "मास्को",
                            "ms": "Moscow",
                            "ia": "Moscova",
                            "iu": "ᒨᔅᑯ",
                            "oc": "Moscòu",
                            "tt": "Мәскәү"
                        },
                        "lat": 55.7504461,
                        "lon": 37.6174943,
                        "country": "RU",
                        "state": "Moscow"
                    },
                    {
                        "name": "Moscow",
                        "local_names": {
                            "en": "Moscow",
                            "ru": "Москва"
                        },
                        "lat": 46.7323875,
                        "lon": -117.0001651,
                        "country": "US",
                        "state": "Idaho"
                    },
                    {
                        "name": "Moscow",
                        "lat": 45.071096,
                        "lon": -69.891586,
                        "country": "US",
                        "state": "Maine"
                    },
                    {
                        "name": "Moscow",
                        "lat": 35.0619984,
                        "lon": -89.4039612,
                        "country": "US",
                        "state": "Tennessee"
                    },
                    {
                        "name": "Moscow",
                        "lat": 39.5437014,
                        "lon": -79.0050273,
                        "country": "US",
                        "state": "Maryland"
                    }
                ]
            """;
    private HttpClient mockClient = mock(HttpClient.class);
    private OpenWeatherApiService openWeatherAPIService = new OpenWeatherApiService(mockClient);

    @Test
    void shouldGetLocationsForParticularCity() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(response);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String cityName = "Moscow";

        List<LocationDTO> locations = openWeatherAPIService.getLocationsByCityName(cityName);

        boolean isAllLocationNamesEqualsToCityName = true;
        for(LocationDTO location : locations) {
            if(location.getName().equals(cityName) == false) {
                isAllLocationNamesEqualsToCityName = false;
            }
        }

        assertTrue(isAllLocationNamesEqualsToCityName);
    }

    @Test
    void errorCodeFromWeatherApiShouldThrowException() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockResponse.body()).thenReturn(response);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String cityName = "Moscow";


        assertThrows(WeatherApiCallException.class, () ->
                openWeatherAPIService.getLocationsByCityName(cityName));
    }
}