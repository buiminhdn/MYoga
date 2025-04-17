import { StyleSheet } from "react-native";
import colors from "../../assets/styles/Colors";

const styles = StyleSheet.create({
    container: {
        paddingVertical: 20,
        backgroundColor: "#F7F8FA",
        flexGrow: 1,
      },
      title: {
        fontSize: 24,
        fontWeight: "bold",
        textAlign: "center",
        marginTop: 30,
        marginBottom: 10,
      },
      subtitle: {
        fontSize: 16,
        color: "#6c6c6c",
        textAlign: "center",
        marginBottom: 20,
      },
      orderedClassesContainer: {
        paddingBottom: 20,
      },
      divider: {
        marginHorizontal: 16,
        height: 1,
        backgroundColor: "#E0E0E0",
        marginVertical: 20,
      },
      sectionTitle: {
        fontSize: 16,
        fontWeight: "bold",
        marginHorizontal: 16,
        marginBottom: 10,
      },
      input: {
        marginHorizontal: 16,
        height: 50,
        borderColor: "#E0E0E0",
        borderWidth: 1,
        borderRadius: 8,
        paddingHorizontal: 15,
        fontSize: 16,
        marginBottom: 15,
        backgroundColor: "#fff",
      },
      placeOrderButton: {
        marginHorizontal: 16,
        backgroundColor: "#007BFF",
        paddingVertical: 15,
        alignItems: "center",
        borderRadius: 8,
        marginTop: 10,
        flexDirection: "row",
        justifyContent: "center",
        gap: 10,
      },
      placeOrderText: {
        color: "#fff",
        fontSize: 16,
        fontWeight: "bold",
      },
});

export default styles;