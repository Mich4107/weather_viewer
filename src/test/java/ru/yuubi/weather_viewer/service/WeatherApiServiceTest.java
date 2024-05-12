package ru.yuubi.weather_viewer.service;

import org.junit.jupiter.api.Test;
import ru.yuubi.weather_viewer.dto.LocationDTO;
import ru.yuubi.weather_viewer.exception.WeatherApiException;

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

class WeatherApiServiceTest {

    private static final String response = """
                [
                    {
                        "name": "Moscow",
                        "local_names": {
                            "ar": "موسكو",
                            "hi": "मास्को",
                            "an": "Moscú",
                            "az": "Moskva",
                            "dv": "މޮސްކޯ",
                            "sc": "Mosca",
                            "be": "Масква",
                            "lv": "Maskava",
                            "ml": "മോസ്കോ",
                            "id": "Moskwa",
                            "gd": "Moscobha",
                            "de": "Moskau",
                            "nb": "Moskva",
                            "ak": "Moscow",
                            "wo": "Mosku",
                            "hy": "Մոսկվա",
                            "sr": "Москва",
                            "it": "Mosca",
                            "eo": "Moskvo",
                            "bs": "Moskva",
                            "ch": "Moscow",
                            "fr": "Moscou",
                            "ca": "Moscou",
                            "is": "Moskva",
                            "mk": "Москва",
                            "te": "మాస్కో",
                            "wa": "Moscou",
                            "ab": "Москва",
                            "zu": "IMoskwa",
                            "mt": "Moska",
                            "so": "Moskow",
                            "ur": "ماسکو",
                            "cy": "Moscfa",
                            "af": "Moskou",
                            "ascii": "Moscow",
                            "ba": "Мәскәү",
                            "kw": "Moskva",
                            "ug": "Moskwa",
                            "av": "Москва",
                            "et": "Moskva",
                            "sm": "Moscow",
                            "oc": "Moscòu",
                            "mr": "मॉस्को",
                            "uk": "Москва",
                            "mn": "Москва",
                            "tt": "Мәскәү",
                            "su": "Moskwa",
                            "am": "ሞስኮ",
                            "mi": "Mohikau",
                            "sv": "Moskva",
                            "cs": "Moskva",
                            "he": "מוסקווה",
                            "se": "Moskva",
                            "zh": "莫斯科",
                            "pt": "Moscou",
                            "ln": "Moskú",
                            "bi": "Moskow",
                            "kv": "Мӧскуа",
                            "vi": "Mát-xcơ-va",
                            "iu": "ᒨᔅᑯ",
                            "da": "Moskva",
                            "hr": "Moskva",
                            "sq": "Moska",
                            "br": "Moskov",
                            "mg": "Moskva",
                            "co": "Moscù",
                            "uz": "Moskva",
                            "cv": "Мускав",
                            "tl": "Moscow",
                            "my": "မော်စကိုမြို့",
                            "pl": "Moskwa",
                            "na": "Moscow",
                            "ga": "Moscó",
                            "ps": "مسکو",
                            "bn": "মস্কো",
                            "za": "Moscow",
                            "ht": "Moskou",
                            "nl": "Moskou",
                            "fo": "Moskva",
                            "dz": "མོསི་ཀོ",
                            "kl": "Moskva",
                            "bg": "Москва",
                            "ru": "Москва",
                            "sk": "Moskva",
                            "nn": "Moskva",
                            "jv": "Moskwa",
                            "ta": "மாஸ்கோ",
                            "yo": "Mọsko",
                            "ka": "მოსკოვი",
                            "lg": "Moosko",
                            "sl": "Moskva",
                            "ia": "Moscova",
                            "qu": "Moskwa",
                            "tr": "Moskova",
                            "sg": "Moscow",
                            "sw": "Moscow",
                            "hu": "Moszkva",
                            "lt": "Maskva",
                            "ja": "モスクワ",
                            "tk": "Moskwa",
                            "gl": "Moscova - Москва",
                            "fi": "Moskova",
                            "kk": "Мәскеу",
                            "st": "Moscow",
                            "es": "Moscú",
                            "ss": "Moscow",
                            "ay": "Mosku",
                            "ky": "Москва",
                            "th": "มอสโก",
                            "ty": "Moscou",
                            "yi": "מאסקווע",
                            "gv": "Moscow",
                            "ms": "Moscow",
                            "li": "Moskou",
                            "sh": "Moskva",
                            "os": "Мæскуы",
                            "eu": "Mosku",
                            "ro": "Moscova",
                            "vo": "Moskva",
                            "feature_name": "Moscow",
                            "cu": "Москъва",
                            "ku": "Moskow",
                            "ce": "Москох",
                            "bo": "མོ་སི་ཁོ།",
                            "el": "Μόσχα",
                            "no": "Moskva",
                            "tg": "Маскав",
                            "gn": "Mosku",
                            "ko": "모스크바",
                            "io": "Moskva",
                            "la": "Moscua",
                            "fy": "Moskou",
                            "kg": "Moskva",
                            "ie": "Moskwa",
                            "fa": "مسکو",
                            "kn": "ಮಾಸ್ಕೋ",
                            "en": "Moscow"
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
                        "name": "Berkarar obasy",
                        "local_names": {
                            "tk": "Berkarar obasy"
                        },
                        "lat": 37.41866695,
                        "lon": 60.42703721312893,
                        "country": "TM",
                        "state": "Ahal Region"
                    },
                    {
                        "name": "Berkarar obasy",
                        "local_names": {
                            "tk": "Berkarar obasy"
                        },
                        "lat": 37.4150582,
                        "lon": 60.4266397,
                        "country": "TM",
                        "state": "Ahal Region"
                    },
                    {
                        "name": "Moskwa",
                        "local_names": {
                            "pl": "Moskwa",
                            "ru": "Москва"
                        },
                        "lat": 51.8158099,
                        "lon": 19.6573685,
                        "country": "PL",
                        "state": "Łódź Voivodeship"
                    }
                ]
            """;
    private HttpClient mockClient = mock(HttpClient.class);
    private WeatherApiService weatherAPIService = new WeatherApiService(mockClient);
    private String wrongUrl = "https://api.openweathermap.org/data/2.5/weather?lat=0&lon=0&appid=null";

    @Test
    void shouldGetLocationsForParticularCity() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(response);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        String cityName = "Moscow";

        List<LocationDTO> locations = weatherAPIService.getLocationsByCityName(cityName);

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


        assertThrows(WeatherApiException.class, () ->
                weatherAPIService.getLocationsByCityName(cityName));
    }
}