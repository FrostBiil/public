using UnityEngine;
using TMPro;
using UnityEngine.UI;

public class Text : CustomUIComponent
{
    public TextSO textData;
    public Style style;

    public TextMeshProUGUI textMeshProUGUI;

    public override void Setup()
    {
        textMeshProUGUI = GetComponent<TextMeshProUGUI>();
    }


    public override void Configure()
    {
        textMeshProUGUI.color = textData.theme.GetTextColor(style);
        textMeshProUGUI.font = textData.font;
        textMeshProUGUI.fontSize = textData.size;
    }
}
