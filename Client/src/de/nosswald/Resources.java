package tech.kaij.space_shooter;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public final class Resources
{
    public static final Font MainFont = GetMainFont();
    private static final Font GetMainFont() {
        // TODO: Consider following FluentUI here - use SegoeUI
        try
        {
            InputStream r = Thread.currentThread().getContextClassLoader().getResourceAsStream("Exo2-RegularExpanded.ttf");
            Font f = Font.createFont(Font.TRUETYPE_FONT, r);
            r.close();
            return f;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.exit(-1);
        return null; // never reached;
    }

    public static final Font FontXL = MainFont.deriveFont(68.0f);
    public static final Font FontHeroTitle = MainFont.deriveFont(42.0f);
    public static final Font FontGreetingTitle = MainFont.deriveFont(32.0f);
    public static final Font FontPageTitle = MainFont.deriveFont(28.0f);
    public static final Font FontPaneHeader = MainFont.deriveFont(20.0f);
    public static final Font FontHeader = MainFont.deriveFont(18.0f);
    public static final Font FontSubjectTitle = MainFont.deriveFont(16.0f);
    public static final Font FontBodyText = MainFont.deriveFont(14.0f);
    public static final Font FontMetadata = MainFont.deriveFont(12.0f);
    public static final Font FontMetadataLimited = MainFont.deriveFont(10.0f);

    public static final Color SelfColor = new Color(0x00, 0xE0, 0xFF);
}
