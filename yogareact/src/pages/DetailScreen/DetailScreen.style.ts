import { StyleSheet } from "react-native";
import colors from "../../assets/styles/Colors";

const styles = StyleSheet.create({
    container: {
        padding: 20,
        backgroundColor: "#F7F8FA",
        flexGrow: 1,
      },
      backButton: {
        position: "absolute",
        top: 40,
        left: 16,
        padding: 10,
        backgroundColor: colors.paleBlue,
        borderRadius: 8,
      },
      title: {
        fontSize: 24,
        fontWeight: "bold",
        textAlign: "center",
        marginTop: 30,
        marginBottom: 10,
      },
      description: {
        fontSize: 16,
        color: "#6c6c6c",
        marginVertical: 10,
      },
      infoContainer: {
        marginTop: 10,
        marginBottom: 10,
      },
      divider: {
        height: 1,
        backgroundColor: "#E0E0E0",
        marginVertical: 20,
      },
      addToCartButton: {
        backgroundColor: "#007BFF",
        paddingVertical: 15,
        alignItems: "center",
        borderRadius: 8,
        marginTop: 20,
        flexDirection: "row",
        justifyContent: "center",
        gap: 10,
      },
      addToCartText: {
        color: "#fff",
        fontWeight: "bold",
      },
});

export default styles;