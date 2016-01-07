package lab.funwith.picasso.model;

import lab.funwith.picasso.data.ConstantViewType;

public class ModelData {
    public String pictureUrl;
    public int viewType;

    public ModelData(String pictureUrl, @ConstantViewType.ViewType int viewType) {
        this.pictureUrl = pictureUrl;
        this.viewType = viewType;
    }
}
