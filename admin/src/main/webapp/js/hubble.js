$('.switch').switchButton({
    on_label: 'Sí',
    off_label: 'No'
});

$('#habilitada.switch').switchButton({
    on_label: 'Habilitada',
    off_label: 'Deshabilitada'
});
$('.habilitar.switch').switchButton({
    on_label: 'Habilitar',
    off_label: 'Deshabilitar'
});
$('.tr.switch').switchButton({
    on_label: 'Habilitado',
    off_label: 'Deshabilitado'
});
$('.performance.switch').switchButton({
    on_label: 'Performance',
    off_label: ''
});
$('.disponibilidad.switch').switchButton({
    on_label: 'Disponibilidad',
    off_label: ''
});
$("select.picklist").pickList({
    sourceListLabel: "Disponibles",
    targetListLabel: "Seleccionados"
});
