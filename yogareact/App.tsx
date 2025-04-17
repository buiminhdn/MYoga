import { Ionicons } from "@expo/vector-icons";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeStackNavigator from "./src/navigators/HomeStackNavigator";
import ProfileScreen from "./src/pages/ProfileScreen/ProfileScreen";
import colors from "./src/assets/styles/Colors";
import CartScreen from "./src/pages/CartScreen/CartScreen";
import { UserProvider } from "./src/context/UserContext";

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <UserProvider>
      <NavigationContainer>
        <Tab.Navigator
          screenOptions={({ route }) => ({
            tabBarIcon: ({ color, size }) => {
              let iconName:
                | "home-outline"
                | "cart-outline"
                | "person-outline"
                | undefined;

              if (route.name === "Home") {
                iconName = "home-outline";
              } else if (route.name === "Cart") {
                iconName = "cart-outline";
              } else if (route.name === "Profile") {
                iconName = "person-outline";
              }

              return <Ionicons name={iconName} color={color} size={size} />;
            },
            tabBarLabelStyle: { fontSize: 12 },
            tabBarStyle: { height: 60, paddingBottom: 5, paddingTop: 5 },
            tabBarActiveTintColor: colors.darkBlue,
            tabBarInactiveTintColor: "#8e8e8e",
            headerShown: false,
          })}
        >
          <Tab.Screen name="Home" component={HomeStackNavigator} />
          <Tab.Screen name="Cart" component={CartScreen} />
          <Tab.Screen name="Profile" component={ProfileScreen} />
        </Tab.Navigator>
      </NavigationContainer>
    </UserProvider>
  );
}
