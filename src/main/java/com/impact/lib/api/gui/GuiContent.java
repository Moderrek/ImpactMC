package pl.impact.lib.api.gui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.impact.lib.api.gui.element.EmptyUiElement;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class GuiContent implements GuiContentBase<UiElement> {

    protected transient Gui gui;
    protected final Map<Integer, UiElement> content;
    protected final int size;

    public GuiContent(int size, Map<Integer, UiElement> content) {
        this.size = size;
        this.content = content;
    }

    public GuiContent(@NotNull GuiSize size, Map<Integer, UiElement> content, Gui gui) {
        this(size.getSlotCount(), content);
    }

    protected void initContent(Gui gui) {
        this.gui = gui;
    }

    private void isValidSlotIndex(int slotIndex) {
        if(slotIndex < 0 || slotIndex >= size) throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public final int getGuiSize() {
        return size;
    }

    @Override
    public Collection<UiElement> getUiElements() {
        return content.values();
    }

    @Override
    public Optional<UiElement> getUiElement(int slotIndex) {
        isValidSlotIndex(slotIndex);
        return Optional.ofNullable(content.get(slotIndex));
    }

    @Override
    public Optional<UiElement> addUiElement(@NotNull UiElement value) {
        for (UiElement search : content.values()) {
            if (!(search instanceof EmptyUiElement)) continue;
            final int key = search.getSlotIndex();
            if (content.containsKey(key) && Objects.equals(content.get(key), search)) {
                return Optional.of(setUiElement(key, value));
            }
        }
//        throw new RuntimeException("GUI is full! Cannot add new element");
        return Optional.empty();
    }

    @Override
    public UiElement setUiElement(int slotIndex, @NotNull UiElement element) {
        isValidSlotIndex(slotIndex);
        // init new element
        element.init(gui, slotIndex);
        content.put(slotIndex, element);
        // updating open gui
        gui.update(element);
        return element;
    }

    @Override
    public UiElement removeUiElement(int slotIndex) {
        isValidSlotIndex(slotIndex);
        final UiElement oldElement = getUiElement(slotIndex).orElseThrow();
        setUiElement(slotIndex, new EmptyUiElement());
        return oldElement;
    }

    @Override
    public void fillUiElements(@NotNull UiElement value, @Nullable Consumer<UiElement> action) {
        for (int i = 0; i < size; i += 1) {
            final UiElement toSet = value.clone();
            setUiElement(i, toSet);
            if (action != null) action.accept(toSet);
        }
        gui.update();
    }

    @Override
    public void fillUiElements(@NotNull UiElement value) {
        fillUiElements(value, null);
    }


}
