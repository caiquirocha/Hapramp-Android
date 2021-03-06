/*
 * Copyright (C) 2016 Muhammed Irshad
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hapramp.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.hapramp.editor.Components.CustomEditText;
import com.hapramp.editor.models.EditorContent;
import com.hapramp.editor.models.EditorTextStyle;
import com.hapramp.editor.models.RenderType;

import java.util.Map;

public class Editor extends EditorCore {
  public Editor(Context context, AttributeSet attrs) {
    super(context, attrs);
    super.setEditorListener(null);
    //  initialize(context,parentView,renderType,_PlaceHolderText);
  }

  public void setEditorListener(EditorListener _listener) {
    super.setEditorListener(_listener);
  }

  public String getContentAsSerialized() {
    return super.getContentAsSerialized();
  }

  public String getContentAsSerialized(EditorContent state) {
    return super.getContentAsSerialized(state);
  }

  public EditorContent getContentDeserialized(String EditorContentSerialized) {
    return super.getContentDeserialized(EditorContentSerialized);
  }

  public EditorContent getContent() {
    return super.getContent();
  }

  public void clearAllContents() {
    super.clearAllContents();
    if (getRenderType() == RenderType.Editor) {
      getInputExtensions().insertEditText(0, this.placeHolder, null);
    }
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event, CustomEditText editText) {
    boolean onKey = super.onKey(v, keyCode, event, editText);
    if (getParentChildCount() == 0)
      render();
    return onKey;
  }

  public void render() {
    if (getRenderType() == RenderType.Editor) {
      getInputExtensions().insertEditText(0, this.placeHolder, null);
    }
  }

  public String getContentAsHTML() {
    return getHtmlExtensions().getContentAsHTML();
  }

  public String getContentAsHTML(EditorContent content) {
    return getHtmlExtensions().getContentAsHTML(content);
  }

  public String getContentAsHTML(String editorContentAsSerialized) {
    return getHtmlExtensions().getContentAsHTML(editorContentAsSerialized);
  }

  public void render(String HtmlString) {
    renderEditorFromHtml(HtmlString);
  }

  private void restoreState() {
    EditorContent state = getStateFromString(null);
    render(state);
  }

  public void render(EditorContent _state) {
    super.renderEditor(_state);
  }

  public int getH1TextSize() {
    return getInputExtensions().getH1TextSize();
  }

  /**
   * size in sp
   *
   * @param size
   */
  public void setH1TextSize(int size) {
    // getInputExtensions().setH1TextSize(size);
  }

  public int getH2TextSize() {
    return getInputExtensions().getH2TextSize();
  }

  /**
   * size in sp
   *
   * @param size
   */
  public void setH2TextSize(int size) {
    //getInputExtensions().setH2TextSize(size);
  }

  public int getH3TextSize() {
    return getInputExtensions().getH3TextSize();
  }

  /**
   * size in sp
   *
   * @param size
   */
  public void setH3TextSize(int size) {
    //getInputExtensions().setH3TextSize(size);
  }

  /**
   * size in sp
   *
   * @param size
   */
  public void setNormalTextSize(int size) {
    // getInputExtensions().setNormalTextSize(size);
  }

  /**
   * Set the fontface for the editor
   *
   * @deprecated use {@link #setContentTypeface(Map)} and {@link #setHeadingTypeface(Map)} (Map)} ()} instead.
   */
  @Deprecated
  public void setFontFace(int StringResource) {
    getInputExtensions().setFontFace(StringResource);
  }

  public Typeface getNormalFontTypeface() {
    return Typeface.create(getInputExtensions().getFontFace(), Typeface.NORMAL);
  }

  public Typeface getBoldFontTypeface() {
    return Typeface.create(getInputExtensions().getFontFace(), Typeface.BOLD);
  }

  public void updateTextStyle(EditorTextStyle style) {
    getInputExtensions().UpdateTextStyle(style, null);
  }

  public void insertLink() {
    getInputExtensions().insertLink();
  }

  public void insertLink(String link) {
    getInputExtensions().insertLink(link);
  }

  public Map<Integer, String> getContentTypeface() {
    return getInputExtensions().getContentTypeface();
  }

  /**
   * setup the fontfaces for editor content
   * For eg:
   * Map<Integer, String> typefaceMap = new HashMap<>();
   * typefaceMap.put(Typeface.NORMAL,"fonts/GreycliffCF-Medium.ttf");
   * typefaceMap.put(Typeface.BOLD,"fonts/GreycliffCF-Bold.ttf");
   * typefaceMap.put(Typeface.ITALIC,"fonts/GreycliffCF-Medium.ttf");
   * typefaceMap.put(Typeface.BOLD_ITALIC,"fonts/GreycliffCF-Medium.ttf");
   *
   * @param map
   */

  public void setContentTypeface(Map<Integer, String> map) {
    getInputExtensions().setContentTypeface(map);
  }

  public Map<Integer, String> getHeadingTypeface() {
    return getInputExtensions().getHeadingTypeface();
  }


  /*
   *
   * Divider extension
   *
   */

  /**
   * setup the fontfaces for editor heding tags (h1,h2,h3)
   * for Eg:
   * Map<Integer, String> typefaceMap = new HashMap<>();
   * typefaceMap.put(Typeface.NORMAL,"fonts/GreycliffCF-Medium.ttf");
   * typefaceMap.put(Typeface.BOLD,"fonts/GreycliffCF-Bold.ttf");
   * typefaceMap.put(Typeface.ITALIC,"fonts/GreycliffCF-Medium.ttf");
   * typefaceMap.put(Typeface.BOLD_ITALIC,"fonts/GreycliffCF-Medium.ttf");
   *
   * @param map
   */
  public void setHeadingTypeface(Map<Integer, String> map) {
    getInputExtensions().setHeadingTypeface(map);
  }

  public void setDividerLayout(int layout) {
    this.getDividerExtensions().setDividerLayout(layout);
  }

  /*
   *
   * Image Extension
   *
   */

  public void insertDivider() {
    getDividerExtensions().insertDivider();
  }

  public void setEditorImageLayout(int layout) {
    this.getImageExtensions().setEditorImageLayout(layout);
  }

  public void openImagePicker() {
    getImageExtensions().openImageGallery();
  }

  public void insertImage(Bitmap bitmap, String filePath) {
    getImageExtensions().insertImage(bitmap, filePath, -1, null);
  }

  public void insertYoutubeVideo(String videoId) {
    getYoutubeExtension().insertYoutubeVideo(videoId);
  }

  public void onImageUploadComplete(String url, String imageId) {
    getImageExtensions().onPostUpload(url, imageId);
  }

  public void onImageUploadFailed(String imageId) {
    getImageExtensions().onPostUpload(null, imageId);
  }

  /*
   *
   *List Item extension
   *
   */
  public void setListItemLayout(int layout) {
    this.getListItemExtensions().setListItemTemplate(layout);
  }


  /*
   *
   * Map Extension
   *
   */

  public void insertList(boolean isOrdered) {
    this.getListItemExtensions().insertlist(isOrdered);
  }
}
