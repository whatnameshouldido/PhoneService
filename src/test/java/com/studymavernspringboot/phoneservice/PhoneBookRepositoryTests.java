package com.studymavernspringboot.phoneservice;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneBookRepositoryTests {
    @Test
    public void jsonRepositoryTest() throws Exception {
        PhoneBookJSONRepository repository = new PhoneBookJSONRepository("test.json");

        String json = "{\"phoneNumber\":\"010-0000-0000\",\"name\":\"이말자\",\"id\":7,\"email\":\"asd@gmail.com\",\"group\":\"Jobs\"}";
        JSONParser jsonParser = new JSONParser();
        IPhoneBook phoneBook = null;
        Object obj = jsonParser.parse(json);
        phoneBook = repository.getObjectFromJson((JSONObject)obj);
        assertThat(phoneBook.getId()).isEqualTo(7L);
        assertThat(phoneBook.getName()).isEqualTo("이말자");
        assertThat(phoneBook.getGroup()).isEqualTo(EPhoneGroup.Jobs);
        assertThat(phoneBook.getPhoneNumber()).isEqualTo("010-0000-0000");
        assertThat(phoneBook.getEmail()).isEqualTo("asd@gmail.com");

        IPhoneBook phoneBook2 = new PhoneBook();
        phoneBook2.setId(88L);
        phoneBook2.setName("폰북");
        phoneBook2.setGroup(EPhoneGroup.Hobbies);
        phoneBook2.setPhoneNumber("1111-2222");
        phoneBook2.setEmail("abcd@naver.com");
        JSONObject jobject = repository.getJsonFromObject(phoneBook2);
        assertThat((Long)jobject.get("id")).isEqualTo(88L);
        assertThat((String)jobject.get("name")).isEqualTo("폰북");
        assertThat((EPhoneGroup.valueOf((String)jobject.get("group")))).isEqualTo(EPhoneGroup.Hobbies);
        assertThat((String)jobject.get("phoneNumber")).isEqualTo("1111-2222");
        assertThat((String)jobject.get("email")).isEqualTo("abcd@naver.com");
        assertThat(jobject.toJSONString()).isEqualTo("{\"phoneNumber\":\"1111-2222\",\"name\":\"폰북\",\"id\":88,\"email\":\"abcd@naver.com\",\"group\":\"Hobbies\"}");
    }

    @Test
    public void textRepositoryTest() throws Exception {
        PhoneBookTextRepository repository = new PhoneBookTextRepository("test.json");
        Throwable ex = assertThrows(Exception.class, () -> repository.getObjectFromText(""));
        System.out.println(ex.toString());

        IPhoneBook phoneBook = repository.getObjectFromText("2,강준서,Friends,010-8476-7844,pobyjun@naver.com");
        assertThat(phoneBook.getId()).isEqualTo(2L);
        assertThat(phoneBook.getName()).isEqualTo("강준서");
        assertThat(phoneBook.getGroup()).isEqualTo(EPhoneGroup.Friends);
        assertThat(phoneBook.getPhoneNumber()).isEqualTo("010-8476-7844");
        assertThat(phoneBook.getEmail()).isEqualTo("pobyjun@naver.com");

        String str = repository.getTextFromObject(phoneBook);
        assertThat(str).isEqualTo("2,강준서,Friends,010-8476-7844,pobyjun@naver.com\n");
    }
}
