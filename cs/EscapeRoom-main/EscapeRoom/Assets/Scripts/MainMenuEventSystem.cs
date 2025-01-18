using System.Collections;
using System.Collections.Generic;
using UnityEditor.SearchService;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class MainMenuEventSystem : MonoBehaviour
{
    [SerializeField]
    GameObject mainMenu;

    [SerializeField]
    GameObject settingsMenu;

    [SerializeField]
    string mainScene;

    [SerializeField]
    AudioSource audioSource;

    [SerializeField]
    Slider volumeSlider;

    private void Start()
    {
        mainMenu.SetActive(true);
        settingsMenu.SetActive(false);
    }

    public void StartGame()
    {
        SceneManager.LoadScene(mainScene);
    }

    public void Settings()
    {
        mainMenu.SetActive(false);
        settingsMenu.SetActive(true);
    }

    public void SettingsBack()
    {
        mainMenu.SetActive(true);
        settingsMenu.SetActive(false);
    }

    public void SliderVolume()
    {
        audioSource.volume = volumeSlider.value;
    }

    public void Quit()
    {
        Application.Quit();
    }
}
