package mchorse.mclib.client.gui.framework.elements.buttons;

import mchorse.mclib.McLib;
import mchorse.mclib.client.gui.framework.elements.GuiContext;
import mchorse.mclib.client.gui.framework.elements.context.GuiContextMenu;
import mchorse.mclib.client.gui.framework.elements.context.GuiSimpleContextMenu;
import mchorse.mclib.client.gui.framework.elements.utils.GuiInventoryElement;
import mchorse.mclib.client.gui.utils.Icons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class GuiSlotElement extends GuiClickElement<GuiSlotElement>
{
	public static final ResourceLocation SHIELD = new ResourceLocation("minecraft:textures/items/empty_armor_slot_shield.png");
	public static final ResourceLocation BOOTS = new ResourceLocation("minecraft:textures/items/empty_armor_slot_boots.png");
	public static final ResourceLocation LEGGINGS = new ResourceLocation("minecraft:textures/items/empty_armor_slot_leggings.png");
	public static final ResourceLocation CHESTPLATE = new ResourceLocation("minecraft:textures/items/empty_armor_slot_chestplate.png");
	public static final ResourceLocation HELMET = new ResourceLocation("minecraft:textures/items/empty_armor_slot_helmet.png");

	public int slot;
	public ItemStack stack;
	public boolean selected;

	public GuiSlotElement(Minecraft mc, int slot, Consumer<GuiSlotElement> callback)
	{
		super(mc, callback);

		this.slot = slot;
	}

	@Override
	protected GuiContextMenu createContextMenu()
	{
		if (this.contextMenu == null)
		{
			return new GuiSimpleContextMenu(this.mc)
				.action(Icons.DOWNLOAD, "Drop item down", () -> {})
				.action(Icons.CLOSE, "Clear item", () ->
				{
					this.stack = null;
				});
		}

		return super.createContextMenu();
	}

	@Override
	protected void drawSkin(GuiContext context)
	{
		int border = this.selected ? 0xff000000 + McLib.primaryColor.get() : 0xffffffff;

		if (McLib.enableBorders.get())
		{
			Gui.drawRect(this.area.x + 1, this.area.y, this.area.getX(1) - 1, this.area.getY(1), 0xff000000);
			Gui.drawRect(this.area.x, this.area.y + 1, this.area.getX(1), this.area.getY(1) - 1, 0xff000000);
			Gui.drawRect(this.area.x + 1, this.area.y + 1, this.area.getX(1) - 1, this.area.getY(1) - 1, border);
			Gui.drawRect(this.area.x + 2, this.area.y + 2, this.area.getX(1) - 2, this.area.getY(1) - 2, 0xffc6c6c6);
		}
		else
		{
			Gui.drawRect(this.area.x, this.area.y, this.area.getX(1), this.area.getY(1), border);
			Gui.drawRect(this.area.x + 1, this.area.y + 1, this.area.getX(1) - 1, this.area.getY(1) - 1, 0xffc6c6c6);
		}

		int x = this.area.getX(0.5F) - 8;
		int y = this.area.getY(0.5F) - 8;

		if (this.stack == null && this.slot != 0)
		{
			GlStateManager.enableAlpha();
			GlStateManager.color(1, 1, 1, 1);

			if (this.slot == 1)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(SHIELD);
			}
			else if (this.slot == 2)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(BOOTS);
			}
			else if (this.slot == 3)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(LEGGINGS);
			}
			else if (this.slot == 4)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(CHESTPLATE);
			}
			else if (this.slot == 5)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(HELMET);
			}

			Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
		}
		else
		{
			RenderHelper.enableGUIStandardItemLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GlStateManager.enableDepth();

			GuiInventoryElement.drawItemStack(this.stack, x, y, null);

			if (this.area.isInside(context.mouseX, context.mouseY))
			{
				context.setTooltipStack(this.stack);
			}

			GlStateManager.disableDepth();
			RenderHelper.disableStandardItemLighting();
		}
	}
}