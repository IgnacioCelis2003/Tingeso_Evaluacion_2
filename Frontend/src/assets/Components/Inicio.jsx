import React from "react";
import { useEffect, useState } from "react";
import {
    Box,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Toolbar,
    Typography,
    Divider,
    IconButton,
    AppBar,
    CssBaseline,
    useMediaQuery,
  } from "@mui/material";
  import { ThemeProvider, createTheme } from "@mui/material/styles";
  import EventIcon from "@mui/icons-material/Event"; // Ícono para "Reservar"
  import BarChartIcon from "@mui/icons-material/BarChart"; // Ícono para "Reportes"
  import MenuIcon from "@mui/icons-material/Menu"; // Ícono para expandir/abrir
  import ChevronLeftIcon from "@mui/icons-material/ChevronLeft"; // Ícono para colapsar/cerrar
  import DirectionsCarIcon from "@mui/icons-material/DirectionsCar"; // Ícono para el kart
  import Calendario from "./Calendario"; // Ajusta la ruta según tu estructura
  import Reportes from "./Reportes"; // Ajusta la ruta según tu estructura

const theme = createTheme({
    palette: {
      primary: {
        main: "#d32f2f", // Rojo oscuro
        light: "#ff6659", // Rojo más claro para hover/selección
      },
      secondary: {
        main: "#b71c1c", // Rojo más oscuro para contraste
      },
      background: {
        default: "#fafafa", // Fondo del contenido
        paper: "#f5f5f5", // Fondo del sidebar
      },
      text: {
        primary: "#212121",
        secondary: "#757575",
      },
    },
});

export default function Inicio() {

    const [selectedView, setSelectedView] = useState("Reservar"); // Vista por defecto: "Reservar"
    const [isDrawerOpen, setIsDrawerOpen] = useState(true); // Estado para controlar si el sidebar está expandido (escritorio)
    const [isMobileDrawerOpen, setIsMobileDrawerOpen] = useState(false); // Estado para controlar el sidebar en móviles
  
    const drawerWidth = 240;
    const collapsedWidth = 56; // Ancho cuando está colapsado (escritorio)
  
    // Usamos useMediaQuery para detectar si estamos en una pantalla pequeña
    const isMobile = useMediaQuery(theme.breakpoints.down("sm")); // sm = 600px
  
    const handleViewChange = (view) => {
      setSelectedView(view);
      if (isMobile) {
        setIsMobileDrawerOpen(false); // Cerrar el sidebar en móviles al seleccionar una opción
      }
    };
  
    const toggleDrawer = () => {
      if (isMobile) {
        setIsMobileDrawerOpen(!isMobileDrawerOpen);
      } else {
        setIsDrawerOpen((prev) => {
          console.log("Toggling drawer (desktop), new state:", !prev);
          return !prev;
        });
      }
    };
  
    return (
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Box sx={{ display: "flex" }}>
          {/* Barra superior (AppBar) */}
          <AppBar
            position="fixed"
            sx={{
              zIndex: (theme) => theme.zIndex.drawer + 1,
              backgroundColor: theme.palette.primary.main,
            }}
          >
            <Toolbar>
              <IconButton
                color="inherit"
                edge="start"
                onClick={toggleDrawer}
                sx={{ mr: 2, display: { sm: "none" } }} // Mostrar solo en móviles
              >
                <MenuIcon />
              </IconButton>
              <DirectionsCarIcon sx={{ mr: 1 }} />
              <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
                KartingRM
              </Typography>
            </Toolbar>
          </AppBar>
  
          {/* Sidebar (Drawer) */}
          <Drawer
            sx={{
              width: isMobile ? drawerWidth : isDrawerOpen ? drawerWidth : collapsedWidth,
              flexShrink: 0,
              "& .MuiDrawer-paper": {
                width: isMobile ? drawerWidth : isDrawerOpen ? drawerWidth : collapsedWidth,
                boxSizing: "border-box",
                backgroundColor: theme.palette.background.paper,
                transition: "width 0.3s", // Animación suave al expandir/colapsar
              },
            }}
            variant={isMobile ? "temporary" : "permanent"} // Temporal en móviles, permanente en escritorio
            anchor="left"
            open={isMobile ? isMobileDrawerOpen : true} // Controlar apertura en móviles
            onClose={toggleDrawer} // Cerrar al hacer clic fuera (móviles)
          >
            <Toolbar
              sx={{
                display: "flex",
                justifyContent: isMobile || isDrawerOpen ? "space-between" : "center",
                alignItems: "center",
                minHeight: 64, // Asegura que el Toolbar tenga altura suficiente
              }}
            >
              {(isMobile || isDrawerOpen) && (
                <Typography variant="h6" noWrap component="div" sx={{ color: theme.palette.primary.main }}>
                  Toolpad
                </Typography>
              )}
              <IconButton
                onClick={toggleDrawer}
                sx={{
                  p: 1,
                  color: theme.palette.primary.main, // Color rojo para el ícono
                  display: { xs: "block", sm: "block" }, // Mostrar en todas las pantallas
                }}
              >
                {isMobile ? <ChevronLeftIcon /> : isDrawerOpen ? <ChevronLeftIcon /> : <MenuIcon />}
              </IconButton>
            </Toolbar>
            <Divider />
            <List>
              {(isMobile || isDrawerOpen) && (
                <Typography variant="subtitle2" sx={{ pl: 2, pt: 1, pb: 1, color: "text.secondary" }}>
                  Herramientas
                </Typography>
              )}
              <ListItem disablePadding>
                <ListItemButton
                  selected={selectedView === "Reservar"}
                  onClick={() => handleViewChange("Reservar")}
                  sx={{
                    backgroundColor: selectedView === "Reservar" ? theme.palette.primary.light : "transparent",
                    "&:hover": {
                      backgroundColor: theme.palette.primary.light,
                    },
                    minHeight: 48,
                    justifyContent: isMobile || isDrawerOpen ? "initial" : "center",
                    px: 2.5,
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: isMobile || isDrawerOpen ? 3 : "auto",
                      justifyContent: "center",
                    }}
                  >
                    <EventIcon sx={{ color: selectedView === "Reservar" ? theme.palette.primary.main : "inherit" }} />
                  </ListItemIcon>
                  <ListItemText
                    primary="Reservar"
                    sx={{ opacity: isMobile || isDrawerOpen ? 1 : 0 }}
                  />
                </ListItemButton>
              </ListItem>
              <ListItem disablePadding>
                <ListItemButton
                  selected={selectedView === "Reportes"}
                  onClick={() => handleViewChange("Reportes")}
                  sx={{
                    backgroundColor: selectedView === "Reportes" ? theme.palette.primary.light : "transparent",
                    "&:hover": {
                      backgroundColor: theme.palette.primary.light,
                    },
                    minHeight: 48,
                    justifyContent: isMobile || isDrawerOpen ? "initial" : "center",
                    px: 2.5,
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: isMobile || isDrawerOpen ? 3 : "auto",
                      justifyContent: "center",
                    }}
                  >
                    <BarChartIcon sx={{ color: selectedView === "Reportes" ? theme.palette.primary.main : "inherit" }} />
                  </ListItemIcon>
                  <ListItemText
                    primary="Reportes"
                    sx={{ opacity: isMobile || isDrawerOpen ? 1 : 0 }}
                  />
                </ListItemButton>
              </ListItem>
            </List>
          </Drawer>
  
          {/* Contenido principal */}
          <Box
            component="main"
            sx={{
              flexGrow: 1,
              p: 3,
              width: isMobile
                ? "100%"
                : { sm: `calc(100% - ${isDrawerOpen ? drawerWidth : collapsedWidth}px)` },
              backgroundColor: theme.palette.background.default,
              transition: "width 0.3s", // Animación suave al ajustar el ancho del contenido
            }}
          >
            <Toolbar /> {/* Espacio para el AppBar */}
            <Typography variant="h4" gutterBottom sx={{ fontSize: { xs: "1.5rem", sm: "2rem" } }}>
              {selectedView}
            </Typography>
            {selectedView === "Reservar" ? <Calendario /> : <Reportes />}
          </Box>
        </Box>
      </ThemeProvider>
    );
}
