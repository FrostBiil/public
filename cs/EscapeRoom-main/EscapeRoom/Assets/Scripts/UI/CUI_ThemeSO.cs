using UnityEngine;

[CreateAssetMenu(menuName ="CustomUI/ThemeSO", fileName ="ThemeSO")]
public class ThemeSO : ScriptableObject
{
    [Header("Primary")]
    public Color primary_bg;
    public Color primary_text;

    [Header("Secondary")]
    public Color secondary_bg;
    public Color secondary_text;

    [Header("Tertiary")]
    public Color tertiary_bg;
    public Color tertiary_text;

    [Header("Quaternary")]
    public Color quaternary_bg;
    public Color quaternary_text;

    [Header("Quinary")]
    public Color quinary_bg;
    public Color quinary_text;

    [Header("Other")]
    public Color disable;

    public Color GetBackgroundColor(Style style)
    {
        if (style == Style.Primary) return primary_bg;
        if (style == Style.Secondary) return secondary_bg;
        if (style == Style.Tertiary) return tertiary_bg;
        if (style == Style.Quaternary) return quaternary_bg;
        if (style == Style.Quinary) return quinary_bg;
        return disable;
    }
    
    public Color GetTextColor(Style style)
    {
        if (style == Style.Primary) return primary_text;
        if (style == Style.Secondary) return secondary_text;
        if (style == Style.Tertiary) return tertiary_text;
        if (style == Style.Quaternary) return quaternary_text;
        if (style == Style.Quinary) return quinary_text;
        return disable;
    }

}
