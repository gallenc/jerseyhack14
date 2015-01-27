<?php
/*
 * Plugin Name: easy-digital-downloads-osgi-licences
 * Version: 1.0
 * Plugin URI: http://opennms.co.uk/
 * Description: Adds OSGi licence management to Easy Digital Downloads.
 * Author: Craig Gallen
 * Author URI: http://www.craiggallen.com/
 * Requires at least: 4.0
 * Tested up to: 4.0
 *
 * Text Domain: eddosgilicences
 * Domain Path: /lang/
 *
 * @package WordPress
 * @author Craig Gallen
 * @since 1.0.0
 */

if ( ! defined( 'ABSPATH' ) ) exit;

// Load plugin class files
require_once( 'includes/class-eddosgilicences.php' );
require_once( 'includes/class-eddosgilicences-settings.php' );

// Load plugin libraries
require_once( 'includes/lib/class-eddosgilicences-admin-api.php' );
require_once( 'includes/lib/class-eddosgilicences-post-type.php' );
require_once( 'includes/lib/class-eddosgilicences-taxonomy.php' );

// load page templates
require_once( 'includes/lib/class-eddosgilicences-page-templates.php' );

/**
 * Returns the main instance of eddOsgiLicences to prevent the need to use globals.
 *
 * @since  1.0.0
 * @return object eddOsgiLicences
 */
function eddOsgiLicences () {
	$instance = eddOsgiLicences::instance( __FILE__, '1.0.0' );

	if ( is_null( $instance->settings ) ) {
		$instance->settings = eddOsgiLicences_Settings::instance( $instance );
	}

	return $instance;
}

eddOsgiLicences();