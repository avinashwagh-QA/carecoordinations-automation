package repository;

import model.TestUser;
import model.TestUserConfig;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private static Map<String, TestUser> userMap;

    static {
        loadUsers();
    }

    private static void loadUsers(){

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = UserRepository.class.getClassLoader()
                    .getResourceAsStream("Cofig/test-users.json");

            TestUserConfig config =
                    mapper.readValue(inputStream, TestUserConfig.class);

            userMap = config.getUsers()
                    .stream()
                    .collect(Collectors.toMap(TestUser::getKey, user ->user ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test users JSON", e);
        }
    }

    public static TestUser getUser(String key){
        return userMap.get(key);
    }

}
