// Importering af useContext og useMemo fra react til at bruge hooks
import React, { useContext, useMemo } from "react";

// Importering af AuthContext fra AuthProvider
import { AuthContext } from "../contexts/AuthProvider";

// Importering af Link fra react-router-dom
import { Link } from "react-router-dom";

// Importering af nødvendige komponenter fra Mantine Core
import {
  ActionIcon,
  Avatar,
  Divider,
  Flex,
  useMantineTheme,
  Space,
  Tooltip,
} from "@mantine/core";

// Importering af ikoner fra Tabler Icons
import {
  IconBuildingStore,
  IconBooks,
  IconUpload,
  IconAdjustmentsAlt,
  IconBrandGithub,
  IconLogout,
} from "@tabler/icons-react";

// Importering af Api til at logge ud
import { Api } from "../utils/api";

// Definering af NavbarItem komponenten
export function NavbarItem({
  icon,
  label,
  path,
  onClick,
}: {
  icon: React.ReactNode;
  label: string;
  path: string;
  onClick?: () => void;
}) {
  // Returnering af NavbarItem komponenten
  return (
    <Tooltip label={label} position="right">
      <Link to={path}>
        <ActionIcon
          onClick={onClick}
          color="white"
          variant="light"
          size="xl"
          radius="xl"
          aria-label={label}
        >
          {icon}
        </ActionIcon>
      </Link>
    </Tooltip>
  );
}

// Definering af Navbar komponenten
export function Navbar() {
  // Definering af bruger og logout fra AuthContext
  const { user, logout } = useContext(AuthContext);
  
  // Definering af theme fra useMantineTheme
  const theme = useMantineTheme();

  // Definering af navbarItems med useMemo
  const navbarItems = useMemo(
    () => [
      { icon: <IconBuildingStore />, label: "Butik", path: "/butik" },
      { icon: <IconBooks />, label: "Bibliotek", path: "/bibliotek" },
      { icon: <IconUpload />, label: "Upload", path: "/upload" },
      { icon: <IconAdjustmentsAlt />, label: "Indstillinger", path: "/indstillinger" },
      { icon: <IconLogout />, label: "Log ud", onClick: () => logout() },
    ],
    [logout]
  );

  // Returnering af Navbar komponenten
  return (
    <Flex
      mih={"100%"}
      gap="md"
      justify="flex-start"
      align="center"
      direction="column"
      wrap="wrap"
      py="md"
      bg={theme.primaryColor}
    >
      {user ? (
        <Tooltip label={user?.displayName} position="right">
          <Avatar size="md" src={user?.photo} />
        </Tooltip>
      ) : (
        <NavbarItem
          icon={<IconBrandGithub />}
          label={"Sign In"}
          path={"#"}
          onClick={() => Api.login()}
        />
      )}

      <Divider w="50%" />

      {navbarItems.slice(0, navbarItems.length - 2).map((item, index) => (
        <NavbarItem
          key={index}
          icon={item.icon}
          label={item.label}
          path={item.path ?? "#"}
        />
      ))}

      <Space style={{ flexGrow: 1 }}></Space>

      <Divider w="50%" />

      {navbarItems
        .slice(navbarItems.length - 2, navbarItems.length)
        .map((item, index) => (
          <NavbarItem
            key={index}
            icon={item.icon}
            label={item.label}
            path={item.path ?? "#"}
            onClick={item.onClick}
          />
        ))}
    </Flex>
  );
}
