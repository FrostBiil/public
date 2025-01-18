using UnityEngine;

public class VRHandController : MonoBehaviour
{
    public float handRadius = 0.1f;
    public LayerMask interactableLayer;

    void Update()
    {
        bool isHandNearObject = false;

        Collider[] hitColliders = Physics.OverlapSphere(transform.position, handRadius, interactableLayer);
        foreach (var hitCollider in hitColliders)
        {
            hitCollider.SendMessage("OnHandHover", SendMessageOptions.DontRequireReceiver);
            isHandNearObject = true;
        }

        if (!isHandNearObject)
        {
            SendMessage("OnHandExit", SendMessageOptions.DontRequireReceiver);
        }
    }
}
