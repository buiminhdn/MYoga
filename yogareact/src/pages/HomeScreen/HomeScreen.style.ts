import { StyleSheet } from "react-native";
import colors from "../../assets/styles/Colors";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#F4F9FF",
        paddingTop: 50,
      },
      searchInput: {
        height: 50,
        backgroundColor: "#FFFFFF",
        borderRadius: 10,
        paddingHorizontal: 16,
        marginHorizontal: 16,
        marginBottom: 16,
        shadowColor: "#000000",
        shadowOffset: {
          width: 0,
          height: 10,
        },
        shadowOpacity: 0.23,
        shadowRadius: 11.27,
        elevation: 14,
      },
      picker: {
        height: 50,
        width: "100%",
        borderWidth: 1,
        borderColor: "#ccc",
        borderRadius: 5,
      },
      classCount: {
        marginHorizontal: 16,
        fontSize: 18,
        fontWeight: "bold",
        marginBottom: 5,
        color: colors.darkText,
      },
      classList: {
        paddingTop: 10,
        paddingBottom: 20,
      },
});

export default styles;