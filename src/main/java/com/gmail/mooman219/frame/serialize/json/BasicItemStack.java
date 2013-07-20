package com.gmail.mooman219.frame.serialize.json;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;
import com.google.gson.annotations.SerializedName;

public class BasicItemStack implements JsonData {
    // Default
    @SerializedName("ID") public int id;
    @SerializedName("Amt") public int amount;
    @SerializedName("Dmg") public short damage;
    @SerializedName("Dat") public byte data;
    // ItemMeta
    @SerializedName("Name") public String displayName;
    @SerializedName("Lore") public List<String> lore;
    // BookMeta
    @SerializedName("Auth") public String author;
    @SerializedName("Title") public String title;
    @SerializedName("Pages") public List<String> pages;
    // LeatherArmorMeta
    @SerializedName("Color") public Color armorColor;
    // SkullMeta
    @SerializedName("Owner") public String owner;

    public BasicItemStack(ItemStack itemstack) {
        id = itemstack.getTypeId();
        damage = itemstack.getDurability();
        amount = itemstack.getAmount();
        data = itemstack.getData().getData();
        if(itemstack.getItemMeta() != null) {
            ItemMeta itemMeta = itemstack.getItemMeta();
            if(itemMeta.hasDisplayName()) {
                displayName = itemMeta.getDisplayName();
            }
            if(itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            }
            if(itemMeta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) itemMeta;
                if(bookMeta.hasAuthor()) {
                    author = bookMeta.getAuthor();
                }
                if(bookMeta.hasTitle()) {
                    title = bookMeta.getTitle();
                }
                if(bookMeta.hasPages()) {
                    pages = bookMeta.getPages();
                }
            } else if(itemMeta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                // getColor is never null
                armorColor = leatherArmorMeta.getColor();
            } else if(itemMeta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                if(skullMeta.hasOwner()) {
                    this.owner = skullMeta.getOwner();
                }
            }
        }
    }

    public ItemStack toItemStack() {
        ItemStack itemstack = new ItemStack(id, amount, damage);
        itemstack.getData().setData(data);
        if(itemstack.getItemMeta() != null) {
            ItemMeta itemMeta = itemstack.getItemMeta();
            if(displayName != null) {
                itemMeta.setDisplayName(displayName);
            }
            if(lore != null) {
                itemMeta.setLore(lore);
            }
            if(itemMeta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) itemMeta;
                if(author != null) {
                    bookMeta.setAuthor(author);
                }
                if(title != null) {
                    bookMeta.setTitle(title);
                }
                if(pages != null) {
                    bookMeta.setPages(pages);
                }
            } else if(itemMeta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                if(armorColor != null) {
                    leatherArmorMeta.setColor(armorColor);
                }
            } else if(itemMeta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                if(owner != null) {
                    skullMeta.setOwner(owner);
                }
            }
            itemstack.setItemMeta(itemMeta);
        }
        return itemstack;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.getGson().toJson(this);
    }

    public static BasicItemStack fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicItemStack.class);
    }

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + ((armorColor == null) ? 0 : armorColor.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + damage;
        result = prime * result + data;
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lore == null) ? 0 : lore.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((pages == null) ? 0 : pages.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicItemStack other = (BasicItemStack) obj;
        if (amount != other.amount) {
            return false;
        }
        if (armorColor == null) {
            if (other.armorColor != null) {
                return false;
            }
        } else if (!armorColor.equals(other.armorColor)) {
            return false;
        }
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (damage != other.damage) {
            return false;
        }
        if (data != other.data) {
            return false;
        }
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        } else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (lore == null) {
            if (other.lore != null) {
                return false;
            }
        } else if (!lore.equals(other.lore)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        if (pages == null) {
            if (other.pages != null) {
                return false;
            }
        } else if (!pages.equals(other.pages)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }
}