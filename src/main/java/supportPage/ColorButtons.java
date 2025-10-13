package supportPage;

public class ColorButtons {

    // конвертер цвета из Hex в rgba
    public static String convertRgbaToHex(String rgba) {
        if (rgba.startsWith("rgba")) {
            String[] values = rgba.replace("rgba(", "").replace(")", "").split(",");
            int r = Integer.parseInt(values[0].trim());
            int g = Integer.parseInt(values[1].trim());
            int b = Integer.parseInt(values[2].trim());
            return String.format("#%02x%02x%02x", r, g, b);
        }
        return rgba;
    }
}
