using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.Events;
using Unity.VisualScripting;

public class CustomButton : CustomUIComponent
{
    public ThemeSO theme;
    public Style style;
    public UnityEvent onClick;

    private Button button;
    private TextMeshPro buttonText;

    private float highlightFactor = 0.1f;
    private float pressedFactor = 0.2f;
    private float selectedFactor = -0.1f;
    private float disabledFactor = -0.2f;



    public override void Setup()
    {
        button = GetComponentInChildren<Button>();
        buttonText = GetComponentInChildren<TextMeshPro>();

    }

    public override void Configure()
    {
        ColorBlock cb = button.colors;
        cb.normalColor = theme.GetBackgroundColor(style);

        cb.highlightedColor = ModifyColor(cb.normalColor, highlightFactor);
        cb.pressedColor = ModifyColor(cb.normalColor, pressedFactor);
        cb.selectedColor = ModifyColor(cb.normalColor, selectedFactor);
        cb.disabledColor = ModifyColor(cb.normalColor, disabledFactor);


        button.colors = cb;

        buttonText.color = theme.GetTextColor(style);
    }

    public void OnClick()
    {
        onClick.Invoke();
    }

    private Color ModifyColor(Color baseColor, float factor)
    {
        return new Color(
            Mathf.Clamp01(baseColor.r + factor),
            Mathf.Clamp01(baseColor.g + factor),
            Mathf.Clamp01(baseColor.b + factor)
        );
    }
}
