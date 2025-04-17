import { createStackNavigator } from "@react-navigation/stack";
import DetailScreen from "../pages/DetailScreen/DetailScreen";
import HomeScreen from "../pages/HomeScreen/HomeScreen";

const Stack = createStackNavigator();

function HomeStackNavigator() {
  return (
    <Stack.Navigator>
      <Stack.Screen
        name="HomeScreen"
        component={HomeScreen}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="DetailScreen"
        component={DetailScreen}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
}

export default HomeStackNavigator;
