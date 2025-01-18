using UnityEngine;

public abstract class CustomUIComponent: MonoBehaviour
{
    private void Awake()
    {
        Init();
    }

    private void Init()
    {
        Setup();
        Configure();
    }

    public abstract void Setup();

    public abstract void Configure();

    private void OnValidate()
    {
        Init();
    }
}
