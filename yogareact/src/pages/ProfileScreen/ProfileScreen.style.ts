import { StyleSheet } from "react-native";
import colors from "../../assets/styles/Colors";

const styles = StyleSheet.create({
    container: {
        paddingVertical: 20,
        backgroundColor: "#F7F8FA",
        flex: 1,
      },
      title: {
        fontSize: 24,
        fontWeight: "bold",
        textAlign: "center",
        marginTop: 30,
        marginBottom: 10,
      },
      sectionTitle: {
        fontSize: 18,
        fontWeight: "bold",
        marginLeft: 16,
        marginTop: 20,
        marginBottom: 10,
      },
      courseList: {
        marginBottom: 20,
      },
      orderContainer: {},
      divider: {
        height: 1,
        backgroundColor: "#E0E0E0",
        marginHorizontal: 16,
        marginVertical: 10,
      },
});

export default styles;