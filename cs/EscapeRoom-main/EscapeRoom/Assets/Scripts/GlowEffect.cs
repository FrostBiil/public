using UnityEngine;

public class GlowEffect : MonoBehaviour
{
    public Material glowMaterial;
    private Material originalMaterial;
    private bool isBeingInteractedWith = false;

    void Start()
    {
        originalMaterial = GetComponent<Renderer>().material;
    }

    public void OnHandHover()
    {
        if (!isBeingInteractedWith)
        {
            GetComponent<Renderer>().material = glowMaterial;
        }
    }

    public void OnHandExit()
    {
        if (!isBeingInteractedWith)
        {
            GetComponent<Renderer>().material = originalMaterial;
        }
    }

    public void OnInteractionStart()
    {
        isBeingInteractedWith = true;
    }

    public void OnInteractionEnd()
    {
        isBeingInteractedWith = false;
        OnHandExit(); 
    }
}
